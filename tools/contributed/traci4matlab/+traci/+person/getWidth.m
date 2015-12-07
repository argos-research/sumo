function width = getWidth(personID)
%getWidth Returns the width in m of this person.
%   width = getWidth(PERSONID) Returns the width in m of this person.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
width = traci.person.getUniversal(constants.VAR_WIDTH, personID);