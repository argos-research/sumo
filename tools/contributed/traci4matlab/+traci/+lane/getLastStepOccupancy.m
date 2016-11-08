function lastStepOccupancy = getLastStepOccupancy(laneID)
%getLastStepOccupancy Get the percentage of occupation on the lane.
%   lastStepOccupancy = getLastStepOccupancy(LANEID) Returns the occupancy 
%   in percentage for the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepOccupancy.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepOccupancy = traci.lane.getUniversal(constants.LAST_STEP_OCCUPANCY, laneID);