function effort = getEffort(edgeID, time)
%getEffort Get the effort used for (re-)routing.
%   effort = getEffort(EDGEID,TIME) Returns the effort value used for 
%   (re-)routing which is valid on the edge at the given time.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getEffort.m 31 2016-09-28 15:16:56Z afacostag $

global message
import traci.constants
traci.beginMessage(constants.CMD_GET_EDGE_VARIABLE, constants.VAR_EDGE_EFFORT,...
    edgeID, 1+4);
message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x'))...
    traci.packInt32(traci.time2steps(time))];
result = traci.checkResult(constants.CMD_GET_EDGE_VARIABLE,...
    constants.VAR_EDGE_EFFORT, edgeID);
effort = result.readDouble();