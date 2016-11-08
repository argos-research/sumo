function lastStepMeanSpeed = getLastStepMeanSpeed(detID)
%getLastStepMeanSpeed Get the average speed.
%   lastStepMeanSpeed = getLastStepMeanSpeed(DETID) Returns the average 
%   speed in m/s for the last time step on the given multi-entry/multi-exit
%   detector.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepMeanSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepMeanSpeed = traci.multientryexit.getUniversal(constants.LAST_STEP_MEAN_SPEED, detID);