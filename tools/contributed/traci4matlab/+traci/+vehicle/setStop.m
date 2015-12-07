function setStop(vehID, edgeID, pos, laneIndex, duration)
%setStop Set a stop for the vehicle.
%   setStop(VEHID,EDGEID) Sets a stop for the vehicle VEHID in the edge
%   EDGEID for the maximum allowed time. The position and the lane index
%   default to one and zero respectively.
%   setStop(...,POS) Specify the position of the stop in the lane.
%   setStop(...,LANEINDEX) Specify the lane index in which the stop will be
%   made.
%   setStop(...,DURATION) Specify the duration of the stop.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$


import traci.constants
global message
if nargin < 5
    duration = 2^31-1;
    if nargin < 4
        laneIndex = 0;
        if nargin < 3
            pos = 1;
        end
    end
end
traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.CMD_STOP,...
    vehID, 1+4+1+4+length(edgeID)+1+8+1+1+1+4);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(4)];
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(edgeID)) uint8(edgeID)];
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(pos) uint8([sscanf(constants.TYPE_BYTE,'%x') ...
    laneIndex sscanf(constants.TYPE_INTEGER,'%x')]) ...
    traci.packInt32(duration)];
traci.sendExact();