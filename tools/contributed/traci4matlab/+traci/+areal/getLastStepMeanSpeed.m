function lastStepMeanSpeed = getLastStepMeanSpeed(detID)
%getLastStepMeanSpeed Get the average speed on the areal detector.
%   lastStepMeanSpeed = getLastStepMeanSpeed(DETID) Returns the average 
%   speed in m/s for the last time step on the given areal detector.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepMeanSpeed = traci.areal.getUniversal(constants.LAST_STEP_MEAN_SPEED, detID);