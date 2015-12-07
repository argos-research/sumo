function lastStepVehicleNumber = getLastStepVehicleNumber(detID)
%getLastStepVehicleNumber Get the number vehicles.
%   lastStepVehicleNumber = getLastStepVehicleNumber(DETID) Returns the 
%   total number of vehicles for the last time step on the given 
%   multi-entry/multi-exit detector.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepVehicleNumber = traci.multientryexit.getUniversal(constants.LAST_STEP_VEHICLE_NUMBER, detID);