function PMxEmission = getPMxEmission(laneID)
%getPmxEmission Get the particular matter emission in the lane.
%   pmxEmission = getPmxEmission(LANEID) Returns the particular matter 
%   emission in mg for the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
PMxEmission = traci.lane.getUniversal(constants.VAR_PMXEMISSION, laneID);