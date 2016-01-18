function program = getProgram(tlsID)
%getProgram Returns the id of the current program.
%   program = getProgram(TLSID) Returns the id of the current program. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getProgram.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
program = traci.trafficlights.getUniversal(constants.TL_CURRENT_PROGRAM, tlsID);