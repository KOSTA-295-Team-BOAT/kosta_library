package util;

import java.io.IOException;

public class ClearScreen {

    public static void clear() {
        final String OS = System.getProperty("os.name").toLowerCase();
        try {
            if (OS.contains("windows")) {
                if (isCmdEnvironment()) {
                    // Windows cmd: cls 명령어
                    new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
                } else {
                    // Git Bash on Windows: ANSI escape로 clear
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } else {
                // Unix-like 시스템 (Linux, macOS, WSL, 등)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            // 최후의 수단: 줄바꿈
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
    private static boolean isCmdEnvironment() {
        // cmd.exe에서는 console이 null이 아님 (IDE는 null임)
        return System.console() != null;
    }
}