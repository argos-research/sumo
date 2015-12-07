function COEmission = getCOEmission(edgeID)
%getCOEmission Returns the CO emission on the given edge.
%   COEmission = getCOEmission(EDGEID) Returns the CO emission in mg for 
%   the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
COEmission = traci.edge.getUniversal(constants.VAR_COEMISSION, edgeID);