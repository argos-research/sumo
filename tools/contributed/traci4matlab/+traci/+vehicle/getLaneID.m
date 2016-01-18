function laneID = getLaneID(vehID)
%getLaneID Returns the ID of the lane where the vehicle was in the last step.
%   laneID = getLaneID(VEHID) Returns the id of the lane the named vehicle 
%   was at within the last step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLaneID.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants


import traci.constants
laneID = traci.vehicle.getUniversal(constants.VAR_LANE_ID, vehID);