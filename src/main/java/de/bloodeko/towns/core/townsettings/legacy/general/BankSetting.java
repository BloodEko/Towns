package de.bloodeko.towns.core.townsettings.legacy.general;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Setting which allows towns get an bank account.
 * The balance is public. Governors can interact with it.
 */
public class BankSetting extends Setting {
    public static final Setting VALUE = new BankSetting();
    
    @Override
    public String getId() {
        return "bank";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(VALUE);
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(VALUE, (BigDecimal) obj);
    }

    @Override
    public void init(Map<Object, Object> map, Integer id) {
        map.put(VALUE, BigDecimal.ZERO);
    }

    @Override
    public String serialize(Map<Object, Object> map) {
        BigDecimal value = (BigDecimal) map.get(VALUE);
        return value.toString();
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        BigDecimal value = new BigDecimal(obj.toString());
        map.put(VALUE, value);
    }
    
    /**
     * Allows members of a town to view the balance and 
     * deposit money, and governors to withdraw.
     */
    public static class BankCommand extends CmdBase {
        private final static String DEPOSIT = Messages.get("cmds.bank.depositCmd");
        private final static String WITHDRAW = Messages.get("cmds.bank.withdrawCmd");
        
        @Override
        public void execute(Player player, String[] args) {
            Town town = getTownAsPlayer(player);
            checkHasBought(town.getSettings(), VALUE);
            Bank bank = new Bank(town.getSettings().getFlags());
            
            if (args.length == 0) {
                Messages.say(player, "cmds.bank.view", bank.getAmount(), bank.currencyName());
                return;
            }
            if (args.length < 2) {
                Messages.say(player, "cmds.bank.syntax");
                return;
            }
            
            if (args[0].equals(DEPOSIT)) {
                getTown(player);
                BigDecimal deposit = parse(args[1]);
                checkMoney(player, deposit.doubleValue());
                Services.economy().withdrawPlayer(player, deposit.doubleValue());
                bank.add(deposit);
                Messages.say(player, "cmds.bank.deposited", deposit, bank.currencyName());
                return;
            }
            if (args[0].equals(WITHDRAW)) {
                getTown(player);
                BigDecimal withdraw = parse(args[1]);
                if (!bank.canSub(withdraw)) {
                    throw new ModifyException("cmds.bank.notEnoughMoney", args[1], bank.currencyName());
                }
                Services.economy().depositPlayer(player, withdraw.doubleValue());
                bank.sub(withdraw);
                Messages.say(player, "cmds.bank.withdrawn", withdraw, bank.currencyName());
                return;
            }
            
            Messages.say(player, "cmds.bank.syntax");
        }
        
        /**
         * Acts as an layer on the raw data to simulate
         * a bank account to interact with.
         */
        private static class Bank {
            private final Map<Object, Object> map;
            
            public Bank(Map<Object, Object> map) {
                this.map = map;
            }

            /**
             * Adds money to the account.
             */
            private void add(BigDecimal amount) {
                VALUE.set(map, getAmount().add(amount));
            }
            
            /**
             * Returns true if after an subtraction
             * doesn't result in a negative balance.
             */
            private boolean canSub(BigDecimal amount) {
                return getAmount().compareTo(amount) >= 0;
            }
            
            /**
             * Subtracts money from the account.
             */
            private void sub(BigDecimal amount) {
                VALUE.set(map, getAmount().subtract(amount));
            }
            
            /**
             * Returns the current balance.
             */
            private BigDecimal getAmount() {
                return (BigDecimal) VALUE.read(map);
            }
            
            /**
             * Returns the display of the currency used.
             */
            private String currencyName() {
                return Services.economy().currencyNamePlural();
            }
        }
        
        /**
         * Parses the input String or displays an error 
         * to the UI if not possible to do so.
         */
        private BigDecimal parse(String str) {
            BigDecimal value;
            try {
                value = new BigDecimal(str);
            } catch (NumberFormatException ex) {
                throw new ModifyException("cmds.bank.invalidInput");
            }
            if (getPlaces(value) > 2) {
                throw new ModifyException("cmds.bank.tooManyPlaces");
            }
            if (value.signum() < 1) {
                throw new ModifyException("cmds.bank.tooSmallInput");
            }
            return value;
        }
        
        /**
         * Returns how many places after comma this value has.
         */
        private static int getPlaces(BigDecimal val) {
            String string = val.stripTrailingZeros().toPlainString();
            int index = string.indexOf(".");
            return index < 0 ? 0 : string.length() - index - 1;
        }
        
        @Override
        public List<String> completeTab(String[] args) {
            if (args.length > 1) {
                return null;
            }
            return Util.filterList(Arrays.asList(DEPOSIT, WITHDRAW), args[0]);
        }
    }

}
