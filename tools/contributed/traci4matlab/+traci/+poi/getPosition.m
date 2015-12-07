function position = getPosition(poiID)
%getPosition Get the position of the poi.
%   position = getPosition(POIID) Returns the position coordinates of 
%   the given poi. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
position = traci.poi.getUniversal(constants.VAR_POSITION, poiID);