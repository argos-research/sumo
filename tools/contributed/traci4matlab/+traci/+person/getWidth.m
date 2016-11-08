function width = getWidth(personID)
%getWidth Returns the width in m of this person.
%   width = getWidth(PERSONID) Returns the width in m of this person.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getWidth.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
width = traci.person.getUniversal(constants.VAR_WIDTH, personID);