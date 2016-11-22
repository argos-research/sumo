function lastStepOccupancy = getLastStepOccupancy(loopID)
%getLastStepOccupancy Get the percentage of time the loop was occupied.
%   lastStepOccupancy = getLastStepOccupancy(LOOPID) Returns the percentage
%   of time the detector was occupied by a vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepOccupancy.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepOccupancy = traci.inductionloop.getUniversal(constants.LAST_STEP_OCCUPANCY, loopID);