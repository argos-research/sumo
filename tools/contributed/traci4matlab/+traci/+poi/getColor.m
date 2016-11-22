function color = getColor(poiID)
%getColor Get the color of the poi.
%   color = getColor(POIID) Returns the rgba color of the given poi.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getColor.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
color = traci.poi.getUniversal(constants.VAR_COLOR, poiID);