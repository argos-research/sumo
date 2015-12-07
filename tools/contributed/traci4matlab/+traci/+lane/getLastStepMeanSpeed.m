function lastStepMeanSpeed = getLastStepMeanSpeed(laneID)
%getLastStepMeanSpeed Get the average speed on the lane.
%   lastStepMeanSpeed = getLastStepMeanSpeed(LANEID) Returns the average 
%   speed in m/s for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepMeanSpeed = traci.lane.getUniversal(constants.LAST_STEP_MEAN_SPEED, laneID);