function lastStepHaltingNumber = getLastStepHaltingNumber(detID)
%getLastStepHaltingNumber Get the number of halting vehicles.
%   lastStepHaltingNumber = getLastStepHaltingNumber(DETID) Returns the 
%   total number of halting vehicles for the last time step on the given 
%   multi-entry/multi-exit detector. A speed of less than 0.1 m/s is 
%   considered a halt.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepHaltingNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepHaltingNumber = traci.multientryexit.getUniversal(constants.LAST_STEP_VEHICLE_HALTING_NUMBER, detID);