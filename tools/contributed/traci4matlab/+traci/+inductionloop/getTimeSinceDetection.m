function timeSinceDetection = getTimeSinceDetection(loopID)
%getTimeSinceDetection Get the time since the last detection.
%   timeSinceDetection = getTimeSinceDetection(LOOPID) Returns the time in 
%   seconds since last detection.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTimeSinceDetection.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
timeSinceDetection = traci.inductionloop.getUniversal(constants.LAST_STEP_TIME_SINCE_DETECTION, loopID);