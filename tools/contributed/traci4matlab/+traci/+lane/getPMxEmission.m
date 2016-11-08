function PMxEmission = getPMxEmission(laneID)
%getPmxEmission Get the particular matter emission in the lane.
%   pmxEmission = getPmxEmission(LANEID) Returns the particular matter 
%   emission in mg for the last time step on the given lane.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPMxEmission.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
PMxEmission = traci.lane.getUniversal(constants.VAR_PMXEMISSION, laneID);