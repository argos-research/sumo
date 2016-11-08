function slowDown(vehID, speed, duration)
%slowDown Reduces the speed of the vehicle.
%   slowDown(VEHID,SPEED,DURATION) Reduces the speed of the vehicle to the 
%   given for the given amount of time.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: slowDown.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.CMD_SLOWDOWN,...
    vehID, 1+4+1+8+1+4);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(2) uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(speed) uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(duration)];
traci.sendExact();