function lastStepOccupancy = getLastStepOccupancy(laneID)
%getLastStepOccupancy Get the percentage of occupation on the lane.
%   lastStepOccupancy = getLastStepOccupancy(LANEID) Returns the occupancy 
%   in percentage for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepOccupancy = traci.lane.getUniversal(constants.LAST_STEP_OCCUPANCY, laneID);