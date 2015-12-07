function sendByteCmd(cmdID, varID, objID, value)
%sendByteCmd An internal function to build a message which sends a byte.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
global message
traci.beginMessage(cmdID, varID, objID, 1+1);
message.string = [message.string  uint8([sscanf(constants.TYPE_BYTE,'%x') value])];
traci.sendExact();