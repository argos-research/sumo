function lastStepVehicleNumber = getLastStepVehicleNumber(laneID)
%getLastStepVehicleNumber Get the number vehicles in the lane.
%   lastStepVehicleNumber = getLastStepVehicleNumber(LANEID) Returns the 
%   total number of vehicles for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepVehicleNumber = traci.lane.getUniversal(...
    constants.LAST_STEP_VEHICLE_NUMBER, laneID);