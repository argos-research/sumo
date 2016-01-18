function adaptedTraveltime = getAdaptedTraveltime(edgeID, time)
%getAdaptedTraveltime Return the travel time value.
%   adaptedTraveltime = getAdaptedTraveltime(EDGEID,TIME) Return the travel
%   time value (in s) used for (re-)routing which is valid on the specified
%   edge at the given time.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   $Id: getAdaptedTraveltime.m 20 2015-03-02 16:52:32Z afacostag $

global message
import traci.constants
traci.beginMessage(constants.CMD_GET_EDGE_VARIABLE,...
    constants.VAR_EDGE_TRAVELTIME,edgeID, 1+4);
message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x'))...
    traci.packInt32(traci.time2steps(time))];
result = traci.checkResult(constants.CMD_GET_EDGE_VARIABLE,...
    constants.VAR_EDGE_TRAVELTIME, edgeID);
adaptedTraveltime = result.readDouble();