function lastStepVehicleNumber = getLastStepVehicleNumber(loopID)
%getLastStepVehicleNumber Get number of vehicles in the loop.
%   lastStepVehicleNumber = getLastStepVehicleNumber(LOOPID) Returns the 
%   number of vehicles that were on the named induction loop within the last simulation step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepVehicleNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepVehicleNumber = traci.inductionloop.getUniversal(...
    constants.LAST_STEP_VEHICLE_NUMBER, loopID);