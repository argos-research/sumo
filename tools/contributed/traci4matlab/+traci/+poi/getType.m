function type = getType(poiID)
%getType Returns the (abstract) type of the poi.
%   type = getType(POIID) Returns the (abstract) type of the poi.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
type = traci.poi.getUniversal(constants.VAR_TYPE, poiID);