package com.bootcamp.smarthome.device;

import com.bootcamp.smarthome.Main;
import com.bootcamp.smarthome.exception.InvalidCommandException;
import com.bootcamp.smarthome.exception.InvalidValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A programmable smart thermostat.
 *
 * Controls the target temperature in Celsius.
 * The valid operating range is 10.0 °C to 35.0 °C (inclusive).
 */
public class SmartThermostat extends Device {
    private static final Logger logger = LoggerFactory.getLogger(SmartThermostat.class);
    /** Current target temperature in Celsius. Valid range: 10.0–35.0 inclusive. */
    private double temperature;

    public SmartThermostat(String deviceId, String name, boolean isOnline) {
        super(deviceId, name, isOnline);
        this.temperature = 20.0;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Sets the target temperature in Celsius.
     *
     * Valid range: 10.0–35.0 inclusive.
     */
    public void setTemperature(double temp) throws InvalidValueException {
        if (temp < 10.0 || temp > 35.0) {
            throw new InvalidValueException("temp", temp, "Must be between 10.0 and 35.0.");
        }
        this.temperature = temp;
        logger.info("{} temperature set to {} °C", getName(), temp);
    }

    @Override
    public void executeCommand(String command) throws InvalidCommandException, InvalidValueException {
        if (command.startsWith("SET_TEMP")) {
            String[] parts = command.split(" ");
            double temp = (parts.length > 1) ? Double.parseDouble(parts[1]) : 20.0;
            setTemperature(temp);
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            throw new InvalidCommandException("Unknown command for SmartThermostat '" + getName() + "':z " + command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | temp=%.1f °C", temperature);
    }
}
