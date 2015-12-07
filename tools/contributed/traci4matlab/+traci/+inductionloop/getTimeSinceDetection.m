function timeSinceDetection = getTimeSinceDetection(loopID)
%getTimeSinceDetection Get the time since the last detection.
%   timeSinceDetection = getTimeSinceDetection(LOOPID) Returns the time in 
%   seconds since last detection.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
timeSinceDetection = traci.inductionloop.getUniversal(constants.LAST_STEP_TIME_SINCE_DETECTION, loopID);