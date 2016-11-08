function bestLanes = getBestLanes(vehID)
%getBestLanes Returns information about the wish to use subsequent lanes
%   bestLanes = getBestLanes(VEHID) Returns information about the wish to 
%   use subsequent edges' lanes, which is stored in a cell array. The
%   information includes laneID, length, occupation, offset, 
%   allowsContinuation and nextLanes. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getBestLanes.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
bestLanes = traci.vehicle.getUniversal(constants.VAR_BEST_LANES, vehID);