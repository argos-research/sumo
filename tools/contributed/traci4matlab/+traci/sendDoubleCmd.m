function sendDoubleCmd(cmdID, varID, objID, value)
%sendDoubleCmd An internal function to build a message which sends a double.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: sendDoubleCmd.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
global message
traci.beginMessage(cmdID, varID, objID, 1+8);
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(value)];
traci.sendExact();