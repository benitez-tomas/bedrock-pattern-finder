package com.mike;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class ProgressBar {
    private final long total;
    private long current = 0;
    private final String label;
    private final long startTime;
    private long lastUpdateTime = 0;

    private Terminal terminal;
    private int barLength;

    public ProgressBar(String label, long total) {
        this.label = label;
        this.total = total;
        this.startTime = System.currentTimeMillis();

        try {
            terminal = TerminalBuilder.terminal();
            updateBarLength();
        } catch (Exception e) {
            barLength = 40;
        }
    }

    private void updateBarLength() {
        int width = terminal.getWidth();
        barLength = Math.max(10, width - 24 - label.length());
    }

    public void step() {
        current++;
        long now = System.currentTimeMillis();
        if (now - lastUpdateTime > 200 || current >= total) {
            print(now);
            lastUpdateTime = now;
        }
    }

    private void print(long now) {
        updateBarLength();

        long percent100 = (current * 10_000L) / total;
        if (percent100 > 10_000) percent100 = 10_000;

        long whole = percent100 / 100;
        long frac = percent100 % 100;

        long filled = (percent100 * barLength) / 10_000L;
        if (filled > barLength) filled = barLength;
        long empty = barLength - filled;

        String bar = "█".repeat((int) filled) + "-".repeat((int) empty);

        long elapsed = now - startTime;
        long remaining = current == 0 ? 0 : (elapsed * (total - current)) / current;
        long remainingSec = remaining / 1000;
        long minutes = remainingSec / 60;
        long seconds = remainingSec % 60;

        System.out.printf(
                "\r[%s] %3d.%02d%% |%s| ETA %02d:%02d\033[0K",
                label, whole, frac, bar, minutes, seconds
        );

        if (current >= total) {
            String finalBar = "=".repeat(barLength);
            System.out.printf(
                    "\r[%s] 100.00%% |%s| ETA 00:00\033[0K\n",
                    label, finalBar
            );
        }

        System.out.flush();
    }

    public void refresh() {
        print(System.currentTimeMillis());
    }
}
