/*
 *       _____  _       _    _____                                _
 *      |  __ \| |     | |  / ____|                              | |
 *      | |__) | | ___ | |_| (___   __ _ _   _  __ _ _ __ ___  __| |
 *      |  ___/| |/ _ \| __|\___ \ / _` | | | |/ _` | '__/ _ \/ _` |
 *      | |    | | (_) | |_ ____) | (_| | |_| | (_| | | |  __/ (_| |
 *      |_|    |_|\___/ \__|_____/ \__, |\__,_|\__,_|_|  \___|\__,_|
 *                                    | |
 *                                    |_|
 *            PlotSquared plot management system for Minecraft
 *                  Copyright (C) 2020 IntellectualSites
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.intellectualsites.plotsquared.plot.config;

import com.github.intellectualsites.plotsquared.plot.object.BlockBucket;
import com.github.intellectualsites.plotsquared.plot.util.StringMan;
import com.sk89q.worldedit.world.block.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration Node.
 */
public class ConfigurationNode {

    private final String constant;
    private final Object defaultValue;
    private final String description;
    private final Configuration.SettingValue type;
    private Object value;

    public ConfigurationNode(String constant, Object defaultValue, String description,
        Configuration.SettingValue type) {
        this.constant = constant;
        this.defaultValue = defaultValue;
        this.description = description;
        this.value = defaultValue;
        this.type = type;
    }

    public Configuration.SettingValue getType() {
        return this.type;
    }

    public boolean isValid(String string) {
        try {
            Object result = this.type.parseString(string);
            return result != null;
        } catch (Exception e) {
            if (e instanceof Configuration.UnknownBlockException) {
                throw e;
            }
            return false;
        }
    }

    public boolean setValue(String string) {
        if (!this.type.validateValue(string)) {
            return false;
        }
        this.value = this.type.parseString(string);
        return true;
    }

    public Object getValue() {
        if (this.value instanceof String[]) {
            return Arrays.asList((String[]) this.value);
        }
        if (this.value instanceof Object[]) {
            List<String> values = new ArrayList<>();
            for (Object value : (Object[]) this.value) {
                values.add(value.toString());
            }
            return values;
        }
        if (this.value instanceof BlockBucket) {
            return this.value.toString();
        }
        if (this.value instanceof BlockState) {
            return this.value.toString();
        }
        return this.value;
    }

    public String getConstant() {
        return this.constant;
    }

    public Object getDefaultValue() {
        if (this.defaultValue instanceof Object[]) {
            return StringMan.join((Object[]) this.defaultValue, ",");
        }
        return this.defaultValue;
    }

    public String getDescription() {
        return this.description;
    }
}
