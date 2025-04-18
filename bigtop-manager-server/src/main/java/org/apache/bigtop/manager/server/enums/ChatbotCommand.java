/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bigtop.manager.server.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ChatbotCommand {
    INFO("info"),
    SEARCH("search"),
    HELP("help");

    private final String cmd;

    ChatbotCommand(String cmd) {
        this.cmd = cmd;
    }

    public static List<String> getAllCommands() {
        List<String> commands = new ArrayList<>();
        for (ChatbotCommand command : ChatbotCommand.values()) {
            commands.add(command.cmd);
        }
        return commands;
    }

    public static ChatbotCommand getCommand(String cmd) {
        for (ChatbotCommand command : ChatbotCommand.values()) {
            if (command.cmd.equals(cmd)) {
                return command;
            }
        }
        return null;
    }

    public static ChatbotCommand getCommandFromMessage(String message) {
        if (message.startsWith("/")) {
            String[] parts = message.split(" ");
            return getCommand(parts[0].substring(1));
        }
        return null;
    }
}
