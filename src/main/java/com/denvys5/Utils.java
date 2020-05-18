package com.denvys5;

import java.util.Optional;

class Utils {
    public static String getRelativeFilepath(){
        Optional<String> optionalPath = Optional.of(ClassLoader.getSystemClassLoader().getResource(".").getPath());
        return optionalPath.orElse(".");
    }
}
