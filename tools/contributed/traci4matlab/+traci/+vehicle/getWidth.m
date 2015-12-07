function width = getWidth(vehID)
%getWidth Returns the width in m of this vehicle.
%   width = getWidth(VEHID) Returns the width in m of this vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
width = traci.vehicle.getUniversal(constants.VAR_WIDTH, vehID);