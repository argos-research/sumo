function lastStepMeanSpeed = getLastStepMeanSpeed(loopID)
%getLastStepMeanSpeed Get the mean speed of the vehicles in the lane.
%   lastStepMeanSpeed = getLastStepMeanSpeed(LOOPID) Returns the mean 
%   speed in m/s of vehicles that were on the named induction loop within 
%   the last simulation step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepMeanSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepMeanSpeed = traci.inductionloop.getUniversal(constants.LAST_STEP_MEAN_SPEED, loopID);