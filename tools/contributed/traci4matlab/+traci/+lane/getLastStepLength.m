function lastStepLength = getLastStepLength(laneID)
%getLastStepLength Get the mean vehicle length on the lane.
%   lastStepLength = getLastStepLength(LANEID) Returns the mean vehicle 
%   length in m for the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepLength = traci.lane.getUniversal(constants.LAST_STEP_LENGTH, laneID);