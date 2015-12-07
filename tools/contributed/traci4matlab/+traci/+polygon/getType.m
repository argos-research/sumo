function type = getType(polygonID)
%getType Returns the (abstract) type of the polygon.
%   type = getType(POLYGONID) Returns the (abstract) type of the polygon.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
type = traci.polygon.getUniversal(constants.VAR_TYPE, polygonID);