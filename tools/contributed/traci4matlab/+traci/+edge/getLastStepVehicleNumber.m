function lastStepVehicleNumber = getLastStepVehicleNumber(edgeID)
%getLastStepVehicleNumber Get the number vehicles in the edge.
%   lastStepVehicleNumber = getLastStepVehicleNumber(EDGEID) Returns the 
%   total number of vehicles for the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepVehicleNumber.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
lastStepVehicleNumber = traci.edge.getUniversal(...
    constants.LAST_STEP_VEHICLE_NUMBER, edgeID);