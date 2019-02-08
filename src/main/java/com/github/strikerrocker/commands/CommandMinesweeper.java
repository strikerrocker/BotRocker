package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CommandMinesweeper extends Command {
    private static Random random = new Random();
    private static int xMax;
    private static int yMax;
    private static String[][] bombLoc;

    public CommandMinesweeper() {
        super("minesweeper", false);
    }

    private static void generateBomb() {
        random.setSeed(System.currentTimeMillis());
        int x = random.nextInt(xMax) + 1;
        int y = random.nextInt(yMax) + 1;
        if (Objects.equals(bombLoc[x][y], "||:bomb:||")) {
            generateBomb();
        } else {
            bombLoc[x][y] = "||:bomb:||";
        }
    }

    private static void generateTileData(int x, int y) {
        if (bombLoc[x][y] == null) {
            int bombsSides = 0;
            for (int yVar = -1; yVar <= 1; yVar++) {
                for (int xVar = -1; xVar <= 1; xVar++) {
                    int xNew = x + xVar;
                    int yNew = y + yVar;
                    if (xNew <= xMax && xNew >= 0 && yNew <= yMax && yNew >= 0) {
                        if (Objects.equals(bombLoc[xNew][yNew], "||:bomb:||")) bombsSides++;
                    }
                }
            }
            switch (bombsSides) {
                case 0:
                    bombLoc[x][y] = "||:zero:||";
                    break;
                case 1:
                    bombLoc[x][y] = "||:one:||";
                    break;
                case 2:
                    bombLoc[x][y] = "||:two:||";
                    break;
                case 3:
                    bombLoc[x][y] = "||:three:||";
                    break;
                case 4:
                    bombLoc[x][y] = "||:four:||";
                    break;
                case 5:
                    bombLoc[x][y] = "||:five:||";
                    break;
                case 6:
                    bombLoc[x][y] = "||:six:||";
                    break;
                case 7:
                    bombLoc[x][y] = "||:seven:||";
                    break;
                case 8:
                    bombLoc[x][y] = "||:eight:||";
                    break;
            }
        }
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        xMax = args.size() > 0 ? Integer.parseInt(args.get(0)) > 0 ? Integer.parseInt(args.get(0)) : 5 : 5;
        yMax = args.size() > 1 ? Integer.parseInt(args.get(1)) > 0 ? Integer.parseInt(args.get(1)) : 5 : 5;
        int bombs = args.size() > 2 ? Integer.parseInt(args.get(2)) > 0 ? Integer.parseInt(args.get(2)) : 5 : 5;
        if (bombs > xMax * yMax) {
            BotUtils.sendMessage(event.getChannel(), "The no of bombs is higher than the available space");
            return;
        }
        bombLoc = new String[xMax + 1][yMax + 1];
        for (int i = 1; i <= bombs; i++) {
            generateBomb();
        }
        for (int y = 1; y <= yMax; y++) {
            StringBuilder msg = new StringBuilder();
            for (int x = 1; x <= xMax; x++) {
                generateTileData(x, y);
                msg.append(" ").append(bombLoc[x][y]);
            }
            BotUtils.sendMessage(event.getChannel(), msg.toString());
        }
    }

    @Override
    public String getDesc() {
        return "Usage : !minesweeper <no of rows> <no of colums> <no of bombs>";
    }
}