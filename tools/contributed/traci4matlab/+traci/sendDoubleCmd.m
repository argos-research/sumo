function sendDoubleCmd(cmdID, varID, objID, value)
%sendDoubleCmd An internal function to build a message which sends a double.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: sendDoubleCmd.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(cmdID, varID, objID, 1+8);
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(value)];
traci.sendExact();