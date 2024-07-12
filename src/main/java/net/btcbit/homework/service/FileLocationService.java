package net.btcbit.homework.service;

import lombok.SneakyThrows;

import java.io.File;
import java.security.CodeSource;

public class FileLocationService {

    @SneakyThrows
    public File getCurrentDir() {
        CodeSource codeSource = FileLocationService.class.getProtectionDomain().getCodeSource();
        File currentLocation = new File(codeSource.getLocation().toURI().getPath());
        return currentLocation.getParentFile();
    }
}
