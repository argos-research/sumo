function lastStepVehicleIDs = getLastStepVehicleIDs(detID)
%getLastStepVehicleIDs Get the IDs of the vehicles.
%   lastStepVehicleIDs = getLastStepVehicleIDs(DETID) Returns cell array 
%   of strings containing the IDs of the vehicles for the last time step 
%   on the given multi-entry/multi-exit detector.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepVehicleIDs.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepVehicleIDs = traci.multientryexit.getUniversal(constants.LAST_STEP_VEHICLE_ID_LIST, detID);