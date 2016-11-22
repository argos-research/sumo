function lastStepLength = getLastStepLength(edgeID)
%getLastStepLength Get the mean vehicle length on the edge.
%   lastStepLength = getLastStepLength(EDGEID) Returns the mean vehicle 
%   length in m for the last time step on the given edge.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepLength = traci.edge.getUniversal(constants.LAST_STEP_LENGTH, edgeID);