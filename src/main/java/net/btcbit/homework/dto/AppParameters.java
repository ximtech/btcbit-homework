package net.btcbit.homework.dto;

public record AppParameters(
        String inputJsonFileName, 
        int interval, 
        String outputJsonFileName) {
}
