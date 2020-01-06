/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.apache.commons.net.util.SubnetUtils;

/**
 *
 * @author Miguelangel
 */
public class Validator {
    private static final String zeroTo255
            = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
 
    private static final String IP_REGEXP
            = zeroTo255 + "\\." + zeroTo255 + "\\."
            + zeroTo255 + "\\." + zeroTo255;
 
    private static final Pattern IP_PATTERN
            = Pattern.compile(IP_REGEXP);
    
    private static final Pattern NUMBER_PATTERN
            = Pattern.compile(zeroTo255);
    
    // Return true when *address* is IP Address
    public static boolean isValidIp(String address) {
        return IP_PATTERN.matcher(address).matches();
    }
    // Return true when *number* is number
    public static byte isValidNumber(String address) {
        if( NUMBER_PATTERN.matcher(address).matches()){
            return Byte.parseByte(address);
        }else{
            return -1;
        }
    }
    /**
     * 
     * @param ipAddress
     * @param mask
     * @return Network of IP
     */
    public static String getNetwork(String ipAddress, String mask){
        
        SubnetUtils utils = new SubnetUtils(ipAddress, mask);
        SubnetUtils.SubnetInfo info = utils.getInfo();
        return info.getNetworkAddress();
    }
    /**
     * 
     * @param fileString
     * @return If string is a file and  Readable
     */
    public static boolean isFile(String fileString) {
        Path file = Paths.get(fileString);
        return Files.isRegularFile(file) & Files.isReadable(file);
    }
    
        /**
     * 
     * @param fileString
     * @return If string is a file and  Readable
     */
    public static boolean isDirectory(String fileString) {
        Path file = Paths.get(fileString);
        return Files.isDirectory(file) & Files.isReadable(file);
    }
    
    
    /*
            String subnet = _vrfIp+"/32";
            SubnetUtils utils = new SubnetUtils(subnet);
            SubnetInfo info = utils.getInfo();

            System.out.printf("Subnet Information for %s:\n", subnet);
            System.out.println("--------------------------------------");
        System.out.printf("IP Address:\t\t\t%s\t[%s]\n", info.getAddress(),
                Integer.toBinaryString(info.asInteger(info.getAddress())));
        System.out.printf("Netmask:\t\t\t%s\t[%s]\n", info.getNetmask(),
                Integer.toBinaryString(info.asInteger(info.getNetmask())));
        System.out.printf("CIDR Representation:\t\t%s\n\n", info.getCidrSignature());

        System.out.printf("Supplied IP Address:\t\t%s\n\n", info.getAddress());

        System.out.printf("Network Address:\t\t%s\t[%s]\n", info.getNetworkAddress(),
                Integer.toBinaryString(info.asInteger(info.getNetworkAddress())));
        System.out.printf("Broadcast Address:\t\t%s\t[%s]\n", info.getBroadcastAddress(),
                Integer.toBinaryString(info.asInteger(info.getBroadcastAddress())));
        System.out.printf("Low Address:\t\t\t%s\t[%s]\n", info.getLowAddress(),
                Integer.toBinaryString(info.asInteger(info.getLowAddress())));
        System.out.printf("High Address:\t\t\t%s\t[%s]\n", info.getHighAddress(),
                Integer.toBinaryString(info.asInteger(info.getHighAddress())));
        System.out.printf("Address List: %s\n\n", Arrays.toString(info.getAllAddresses()));
            */
}
