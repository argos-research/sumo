function COEmission = getCOEmission(laneID)
%getCOEmission Returns the CO emission on the given lane.
%   COEmission = getCOEmission(LANEID) Returns the CO emission in mg for
%   the last time step on the given lane.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
COEmission = traci.lane.getUniversal(constants.VAR_COEMISSION, laneID);