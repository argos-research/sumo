function laneID = getLaneID(vehID)
%getLaneID Returns the ID of the lane where the vehicle was in the last step.
%   laneID = getLaneID(VEHID) Returns the id of the lane the named vehicle 
%   was at within the last step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLaneID.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants


import traci.constants
laneID = traci.vehicle.getUniversal(constants.VAR_LANE_ID, vehID);