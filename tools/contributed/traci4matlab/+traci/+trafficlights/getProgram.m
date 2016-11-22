function program = getProgram(tlsID)
%getProgram Returns the id of the current program.
%   program = getProgram(TLSID) Returns the id of the current program. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getProgram.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
program = traci.trafficlights.getUniversal(constants.TL_CURRENT_PROGRAM, tlsID);