function lastStepVehicleIDs = getLastStepVehicleIDs(edgeID)
%getLastStepVehicleIDs Get the IDs of the vehicles in the edge.
%   lastStepVehicleIDs = getLastStepVehicleIDs(EDGEID) Returns cell array 
%   of strings containing the IDs of the vehicles for the last time step 
%   on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
lastStepVehicleIDs = traci.edge.getUniversal(constants.LAST_STEP_VEHICLE_ID_LIST, edgeID);