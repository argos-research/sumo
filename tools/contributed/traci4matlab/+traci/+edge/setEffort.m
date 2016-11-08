function setEffort(edgeID, effort)
%setEffort Adapt the effort for (-re) routing.
%   setEffort(EDGEID,EFFORT) Adapt the effort value used for (re-)routing 
%   for the given edge.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setEffort.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(constants.CMD_SET_EDGE_VARIABLE, constants.VAR_EDGE_EFFORT, edgeID, 1+4+1+8);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x'))...
    traci.packInt32(1) uint8(sscanf(constants.TYPE_DOUBLE,'%x'))...
    traci.packInt64(effort)];
traci.sendExact();