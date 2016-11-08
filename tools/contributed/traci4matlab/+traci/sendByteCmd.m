function sendByteCmd(cmdID, varID, objID, value)
%sendByteCmd An internal function to build a message which sends a byte.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: sendByteCmd.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(cmdID, varID, objID, 1+1);
message.string = [message.string  uint8([sscanf(constants.TYPE_BYTE,'%x') value])];
traci.sendExact();