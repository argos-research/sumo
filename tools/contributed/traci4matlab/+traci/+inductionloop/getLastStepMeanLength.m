function lastStepMeanLength = getLastStepMeanLength(loopID)
%getLastStepMeanLength Get the mean length of the vehicles in the lane.
%   lastStepMeanLength = getLastStepMeanLength(LOOPID) Returns the mean 
%   length in m of vehicles which were on the detector in the last step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepMeanLength = traci.inductionloop.getUniversal(constants.LAST_STEP_LENGTH, loopID);