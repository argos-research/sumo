function lastStepVehicleNumber = getLastStepVehicleNumber(edgeID)
%getLastStepVehicleNumber Get the number vehicles in the edge.
%   lastStepVehicleNumber = getLastStepVehicleNumber(EDGEID) Returns the 
%   total number of vehicles for the last time step on the given edge.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepVehicleNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lastStepVehicleNumber = traci.edge.getUniversal(...
    constants.LAST_STEP_VEHICLE_NUMBER, edgeID);